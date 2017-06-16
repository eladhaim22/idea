Insert Into Templates Values ('Formulario_Inscripcion')

Insert Into Questions (section,subsection,detail,order_section,order_subsection,order_question,template_id) Values
('Descripción de la idea:',Null,'¿Cuál es su idea? (importante: intente explicarla en menos de 140 caracteres).?',1,NULL,1,(select id from Templates where name = 'Formulario_Inscripcion')),
('Descripción de la idea:',Null,'¿Por qué la considera una oportunidad de negocio?',1,NULL,2,(select id from Templates where name = 'Formulario_Inscripcion')),
('Descripción de la idea:',Null,'¿Cuáles son las necesidades que satisface?',1,NULL,3,(select id from Templates where name = 'Formulario_Inscripcion')),
('Descripción de la idea:',Null,'¿Qué innovación introduce al mercado?',1,NULL,4,(select id from Templates where name = 'Formulario_Inscripcion')),
('Descripción de la idea:',Null,'¿Qué ventaja competitiva posee  su modelo de negocios respecto a sus competidores?',1,Null,5,(select id from Templates where name = 'Formulario_Inscripcion')),
('Actividades:',Null,'Describa las actividades de la futura empresa.',1,NULL,5,(select id from Templates where name = 'Formulario_Inscripcion')),
('Actividades:','Exponer y  comentar (en términos generales):','Las inversiones necesarias (activo fijo necesario, capital de trabajo, etc.),',2,1,1,(select id from Templates where name = 'Formulario_Inscripcion')),
('Actividades:','Exponer y  comentar (en términos generales):','el perfil del personal necesario,',2,1,2,(select id from Templates where name = 'Formulario_Inscripcion')),
('Actividades:','Exponer y  comentar (en términos generales):','el ciclo de explotación ó producción, etc.',2,1,3,(select id from Templates where name = 'Formulario_Inscripcion')),
('Mercado:','Descripción en términos generales de:','la competencia (oferta actual),',2,2,1,(select id from Templates where name = 'Formulario_Inscripcion')),
('Mercado:','Descripción en términos generales de:','clientes,',2,2,2,(select id from Templates where name = 'Formulario_Inscripcion')),
('Mercado:','Descripción en términos generales de:','potencial del mercado,',2,2,3,(select id from Templates where name = 'Formulario_Inscripcion')),
('Mercado:','Descripción en términos generales de:','proveedores, etc.',2,2,4,(select id from Templates where name = 'Formulario_Inscripcion')),
(NULL,NULL,'Consignar en este punto toda otra información complementaria que considere relevante.',3,NULL,1,(select id from Templates where name = 'Formulario_Inscripcion')),
(NULL,NULL,'Explicite, a su juicio, aquellas temáticas en las cuales considera que requerirá de un mayor soporte para la efectiva puesta en marcha de la Idea de Negocio.',4,NULL,1,(select id from Templates where name = 'Formulario_Inscripcion')),
(NULL,NULL,'Si un docente ha promovido la presentación de esta Idea de Negocio, favor de consignar su Nombre y Apellido (y de corresponder, curso donde se efectuó la difusión del concurso).',5,NULL,1,(select id from Templates where name = 'Formulario_Inscripcion'))

Insert Into Templates Values ('Formulario_Evaluacion')
Insert Into Questions (section,subsection,detail,order_section,order_subsection,order_question,template_id) Values
('La idea',NULL,'Descripción de la Idea (de optimo 10 a 0 nula o ausente)',1,Null,1,(select id from Templates where name = 'Formulario_Evaluacion')),
('La idea',NULL,'Identificación de la Oportunidad (de optimo 10 a 0 nula o ausente)',1,Null,2,(select id from Templates where name = 'Formulario_Evaluacion')),
('La idea',NULL,'Nivel de satisfacción potencial de Necesidad/es (de optimo 10 a 0 nula o ausente)',1,Null,3,(select id from Templates where name = 'Formulario_Evaluacion')),
('La idea',NULL,'Grado de Innovación (de optimo 10 a 0 nula o ausente)',1,Null,4,(select id from Templates where name = 'Formulario_Evaluacion')),
('La idea',NULL,'Ventajas competitivas presentadas (de optimo 10 a 0 nula o ausente)',1,Null,5,(select id from Templates where name = 'Formulario_Evaluacion')),
('CRITERIOS (propios) DEL EVALUADOR',NULL,'Propiedad Intelectual (de 10 total propiedad a 0 facilmente copiable)',2,Null,1,(select id from Templates where name = 'Formulario_Evaluacion')),
('CRITERIOS (propios) DEL EVALUADOR',NULL,'Tecnología / soporte tecnológico (de 10 muy viable a 0 no disponible o inexistente)',2,Null,2,(select id from Templates where name = 'Formulario_Evaluacion')),
('CRITERIOS (propios) DEL EVALUADOR',NULL,'Aspectos legales (de 10 pleno cumplimiento a 0 inviable legalmente)',2,Null,3,(select id from Templates where name = 'Formulario_Evaluacion')),
('LAS ACTIVIDADES',NULL,'Descripción de actividades del emprendimiento (de optimo detalle 10 a 0 nula o ausente)',3,Null,1,(select id from Templates where name = 'Formulario_Evaluacion')),
('LAS ACTIVIDADES',NULL,'Detalle de inversiones requeridas (de optimo detalle 10 a 0 nula o ausente)',3,Null,2,(select id from Templates where name = 'Formulario_Evaluacion')),
('LAS ACTIVIDADES',NULL,'Perfiles de capital humano (de optimo detalle 10 a 0 nula o ausente)',3,Null,3,(select id from Templates where name = 'Formulario_Evaluacion')),
('LAS ACTIVIDADES',NULL,'Ciclo de explotación (de optimo detalle 10 a 0 nula o ausente)',3,Null,4,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL MERCADO',NULL,'Descripción / Identificación de competencia (de optimo detalle 10 a 0 nula o ausente)',4,Null,1,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL MERCADO',NULL,'Descripción / Identificación de clientes (de optimo detalle 10 a 0 nula o ausente)',4,Null,2,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL MERCADO',NULL,'Potencial de mercado (de optimo detalle 10 a 0 nula o ausente)',4,Null,3,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL MERCADO',NULL,'Descripción / Identificación de proveedores (de optimo detalle 10 a 0 nula o ausente)',4,Null,4,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL EQUIPO',NULL,'Conocimiento/s de la temática (de perfecto 10 a nulo conocimiento 0)',5,Null,1,(select id from Templates where name = 'Formulario_Evaluacion')),
('EL EQUIPO',NULL,'Experiencia en el sector (de plena 10 a nula 0 experiencia)',5,Null,2,(select id from Templates where name = 'Formulario_Evaluacion'))