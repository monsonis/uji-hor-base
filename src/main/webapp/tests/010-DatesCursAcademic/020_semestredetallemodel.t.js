// also supports: startTest(function(t) {
StartTest(function(t) {
	 t.requireOk('HOR.model.SemestreDetalle', function() {
	        var mod = Ext.create('HOR.model.SemestreDetalle', {
	            id : 1, 
	            fechaFin : '24/09/2012 00:00:00', 
	            nombreSemestre : 'Primer Semestre' 
	        });
	        
	        t.is(mod.get('fechaFin'), new Date(2012,8,24,0,0,0,0), 'Puedo leer la fecha de fin');
	        t.is(mod.get('nombreSemestre'), 'Primer Semestre', 'Puedo leer el nombre del semestre');
	    });

});


