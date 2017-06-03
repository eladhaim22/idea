(function () {
	var module = angular.module('ideaApp');

	module.factory('restService', ['$q', '$http', '$log', '$timeout', function ($q, $http, $log, $timeout) {
		function handleError(error) {
			//debugger;
			$log.error("REST call error follows");
			$log.error(error);
			var serverError = error.data || {};
			var parsedError = {
				serverError: serverError,
				message: typeof serverError === 'string'
					? serverError
					: serverError.exceptionMessage || serverError.message
			};
			if (!parsedError.message) {
				parsedError.message = "Se produjo un error inesperado contactando al servidor.";
			}
			return parsedError;
		}

		function wrapPromise(promise) {
			var deferred = $q.defer();
			promise.then(function (entities) {
				$timeout(function () {
					//entities = jsonPointerResolver.resolve(entities.data);
					entities = entities.data;
					deferred.resolve(entities);
				}, 0);
			}, function (error) {
				deferred.reject(handleError(error));
			});
			return deferred.promise;
		}

		function get(url, config) {
			return wrapPromise($http.get(url, config));
		}

		function post(url, data, config) {
			return wrapPromise($http.post(url, data, config))
		}

		function put(url, data, config) {
			return wrapPromise($http.put(url, data, config))
		}

		function remove(url, config) {
			return wrapPromise($http.delete(url, config));
		}

		function escapeToken(token) {
			return token
			.replaceAll("%", "%25")
			.replaceAll("+", "%2B")
			.replaceAll("/", "%2F")
			.replaceAll("?", "%3F")
			.replaceAll("#", "%23")
			.replaceAll("&", "%26");
		}

		return {
			get: get,
			post: post,
			put: put,
			remove: remove,
			escape: escapeToken
		};
	}]);
})();