
// also supports: startTest(function(t) {
StartTest(function(t) {
	
	t.requireOk([ 'HOR.view.aulas.FiltroAsignacionAulas',
	              'HOR.store.StoreCentros',
	              'HOR.store.StoreEstudios',
	], function() {
		var panel_filtro = Ext.create('HOR.view.aulas.FiltroAsignacionAulas', {
			renderTo : Ext.getBody(),
			width : 800
		});
	
	});
	
	

});