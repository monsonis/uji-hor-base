StartTest(function(t) {
	
    t.diag("Comprobación lógica filtros asignación aulas");

	
	t.waitForCQ('splitbutton', function(splitbutton) {
		
		var centro_seleccionado = 2;
		var estudio_seleccionado = 1;
		var estudio_semestre = 1;
			
		var steps = [
		             { action : 'click', target: '>>splitbutton' }, 
		             { action : 'click', target :'>>menuitem[action="asignacion-aulas]' },
		             { waitFor : 'componentQuery', args : '>>combobox[alias=widget.comboCentros]'},
		             { action : 'click', target :'>>combobox[alias=widget.comboCentros]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboSemestre]')[0];

		            	 t.ok(! combo_centros.disabled, 'El combo de centros NO está deshabilitado');
		            	 t.ok(combo_estudio.disabled, 'El combo de estudios está deshabilitado');
		            	 t.ok(combo_semestre.disabled, 'El combo de semestres está deshabilitado');		            	 		            
		            			            	 
		            	 t.click(combo_centros.getPicker().getNode(centro_seleccionado),next);
		            	
		             },
		             { action : 'click', target :'>>combobox[alias=widget.comboEstudios]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboSemestre]')[0];

		            	 t.ok(combo_centros.getValue() == combo_centros.store.getAt(centro_seleccionado).internalId, 'El combo centros tiene seleccionada la opción que toca');
		            	 t.ok(! combo_centros.disabled, 'El combo de centros NO está deshabilitado');
		            	 t.ok(! combo_estudio.disabled, 'El combo de estudios NO está deshabilitado');
		            	 t.ok(combo_semestre.disabled, 'El combo de semestres está deshabilitado');		         	 		            
		            
		            	 t.click(combo_estudio.getPicker().getNode(estudio_seleccionado), next);
		             }, 
		             { action : 'click', target :'>>combobox[alias=widget.comboSemestre]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboSemestre]')[0];

		            	 t.ok(combo_estudio.getValue() == combo_estudio.store.getAt(estudio_seleccionado).internalId, 'El combo centros tiene seleccionada la opción que toca');
		            	 t.ok(! combo_centros.disabled, 'El combo de centros NO está deshabilitado');
		            	 t.ok(! combo_estudio.disabled, 'El combo de estudios NO está deshabilitado');
		            	 t.ok(! combo_semestre.disabled, 'El combo de semestres NO está deshabilitado');		         	 		            
		            
		            	 t.click(combo_semestre.getPicker().getNode(estudio_semestre), next);
		             },
		             
		             // Segundo pase
		             { action : 'click', target :'>>combobox[alias=widget.comboCentros]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	        	 		            		            			            	 
		            	 t.click(combo_centros.getPicker().getNode(centro_seleccionado+1),next);		            	
		             },
		             { action : 'click', target :'>>combobox[alias=widget.comboEstudios]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboSemestre]')[0];

		            	 t.ok(combo_centros.getValue() == combo_centros.store.getAt(centro_seleccionado+1).internalId, 'El combo centros tiene seleccionada la opción que toca');
		            	 t.ok(! combo_centros.disabled, 'El combo de centros NO está deshabilitado');
		            	 t.ok(! combo_estudio.disabled, 'El combo de estudios NO está deshabilitado');
		            	 t.ok(combo_semestre.disabled, 'El combo de semestres está deshabilitado');		         	 		            
		            
		            	 t.click(combo_estudio.getPicker().getNode(estudio_seleccionado), next);
		             }, 
		             { action : 'click', target :'>>combobox[alias=widget.comboSemestre]' }, 		             
		             function (next) {
		            	 var combo_centros = Ext.ComponentQuery.query('>>combobox[alias=widget.comboCentros]')[0];
		            	 var combo_estudio = Ext.ComponentQuery.query('>>combobox[alias=widget.comboEstudios]')[0];
		            	 var combo_semestre = Ext.ComponentQuery.query('>>combobox[alias=widget.comboSemestre]')[0];

		            	 t.ok(combo_estudio.getValue() == combo_estudio.store.getAt(estudio_seleccionado).internalId, 'El combo centros tiene seleccionada la opción que toca');
		            	 t.ok(! combo_centros.disabled, 'El combo de centros NO está deshabilitado');
		            	 t.ok(! combo_estudio.disabled, 'El combo de estudios NO está deshabilitado');
		            	 t.ok(! combo_semestre.disabled, 'El combo de semestres NO está deshabilitado');		         	 		            
		            
		            	 t.click(combo_semestre.getPicker().getNode(estudio_semestre), next);
		             },
		         ];
		
		t.chain(steps);
		
	});

});