(function () {
	var module = angular.module('ideaApp');

	module.factory('entityServiceFactory', ['$q', 'restService', function ($q, restService) {
		var entitiesCache = {};
		var indexedEntitiesCache = {};

		function defaultParseCriteria(criteria, entityServiceDef) {
			if (!criteria) {
				// assume get all
				return "";
			}
			else if (typeof criteria === 'string') {
				// assume OData query or specific path
				return criteria;
			}
			else if (typeof criteria === 'number') {
				// assume ID
				return "" + criteria;
			}
			else if (typeof criteria === 'object') {
				// assume query
				if (criteria.id) {
					// query by ID
					return "" + criteria.id;
				}
				else {
					// build OData query
					criteria = angular.copy(criteria);
					var pathAndQuery = criteria.path ? "" + criteria.path : "";
					pathAndQuery += pathAndQuery.indexOf("?") < 0 ? "?" : "&";
					angular.forEach(["top", "skip", "filter", "inlinecount", "count", "orderby"], function (value, key) {
						if (criteria[value]) {
							pathAndQuery += "$" + value + "=" + restService.escape(criteria[value]) + "&";
							delete criteria[value];
						}
					});
					// TODO build filter from remaining values
					return pathAndQuery;
				}
			}
		}

		function defaultBaseUrl(entityServiceDef) {
			return "/api/" + entityServiceDef.entityName + "/";
		}

		function defaultFill(entityPromise, entityServiceDef) {
			return entityPromise;
		}

		function defaultGet(criteria, entityService,config) {
			var url = entityService.baseUrl() + entityService.parseCriteria(criteria);
			var getPromise = config ? restService.get(url,config) : restService.get(url);
			return entityService.fill(getPromise);
		}

		function defaultGetIndexed(criteria, entityService) {
			var deferred = $q.defer();
			entityService.get(criteria).then(function (entities) {
				var indexedEntities = _.reduce(entities.length ? entities : [entities], function (ie, e) { ie[e.id] = e; return ie; }, {});
				deferred.resolve(indexedEntities);
			}, function (error) {
				deferred.reject(error);
			});
			return deferred.promise;
		}

		function defaultGetAll(fromCache, entityServiceDef, entityService) {
			var deferred = $q.defer();
			if (fromCache && entitiesCache[entityServiceDef.entityName]) {
				deferred.resolve(angular.copy(entitiesCache[entityServiceDef.entityName]));
			}
			else {
				entityService.get(null).then(function (entities) {
					entitiesCache[entityServiceDef.entityName] = entities;
					deferred.resolve(angular.copy(entities));
				}, function (error) {
					deferred.reject(error);
				});
			}
			return deferred.promise;
		}

		function defaultGetAllIndexed(fromCache, entityServiceDef, entityService) {
			var deferred = $q.defer();
			if (fromCache && indexedEntitiesCache[entityServiceDef.entityName]) {
				deferred.resolve(angular.copy(indexedEntitiesCache[entityServiceDef.entityName]));
			}
			else {
				entityService.getAll(fromCache).then(function (entities) {
					var indexedEntities = _.reduce(entities.length ? entities : [entities], function (ie, e) { ie[e.id] = e; return ie; }, {});
					indexedEntitiesCache[entityServiceDef.entityName] = indexedEntities;
					deferred.resolve(angular.copy(indexedEntities));
				}, function (error) {
					deferred.reject(error);
				});
			}
			return deferred.promise;
		}

		function defaultSave(entity, entityService) {
			var url = entity.id ? entityService.baseUrl() + entity.id : entityService.baseUrl();
			var savePromise = entity.id ? restService.put(url, entity) : restService.post(url, entity);
			return entityService.fill(savePromise);
		}

		function defaultRemove(entityOrId, entityService) {
			var url = typeof entityOrId === "object" ? entityService.baseUrl() + entityOrId.id : entityService.baseUrl() + entityOrId;
			return restService.remove(url);
		}

		function buildFill(wrappedFill, additionalPromisesBuilder, filler) {
			if (arguments.length == 2) {
				filler = additionalPromisesBuilder;
				additionalPromisesBuilder = wrappedFill;
				wrappedFill = null;
			}
			if (arguments.length == 1) {
				filler = wrappedFill;
				wrappedFill = null;
				additionalPromisesBuilder = null;
			}
			return function (entityPromise) {
				entityPromise = wrappedFill ? wrappedFill(entityPromise) : entityPromise;
				var deferred = $q.defer();
				var promises = [entityPromise];
				if (additionalPromisesBuilder) {
					promises = promises.concat(additionalPromisesBuilder());
				}
				$q.all(promises).then(function (result) {
					var entities = result[0];
					result.shift();  // result now contains any additional promises results
					if (entities.hasOwnProperty("length")) { // multiple results
						for (var i = 0; i < entities.length; ++i) {
							filler(entities[i], result);
						}
					}
					else { // single result
						filler(entities, result);
					}
					deferred.resolve(entities);
				}, function (error) {
					deferred.reject(error);
				});
				return deferred.promise;
			}
		}

		function create(entityName) {
			var entityService = {};
			var entityServiceDef = { entityName: entityName };

			entityService.fill = function (entityPromise) {
				return defaultFill(entityPromise, entityServiceDef);
			};

			entityService.parseCriteria = function (criteria) {
				return defaultParseCriteria(criteria, entityServiceDef);
			};

			entityService.baseUrl = function () {
				return defaultBaseUrl(entityServiceDef);
			};

			entityService.get = function (criteria,config) {
				return defaultGet(criteria, entityService,config);
			};

			entityService.getAll = function (fromCache) {
				return defaultGetAll(fromCache, entityServiceDef, entityService);
			};

			entityService.getIndexed = function (criteria) {
				return defaultGetIndexed(criteria, entityService);
			};

			entityService.getAllIndexed = function (fromCache) {
				return defaultGetAllIndexed(fromCache, entityServiceDef, entityService);
			};

			entityService.save = function (entity) {
				return defaultSave(entity, entityService);
			};

			entityService.remove = function (entityOrId) {
				return defaultRemove(entityOrId, entityService);
			};

			return entityService;
		}

		return {
			create: create,
			buildFill: buildFill
		};
	}]);
})();