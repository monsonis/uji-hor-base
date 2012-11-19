// NO FUNCIONA

StartTest(function(t) {

	t.requireOk([ 'HOR.view.aulas.FiltroAsignacionAulas',
			'HOR.store.StoreCentros', 'HOR.store.StoreEstudios', ], function() {
		var store_centros = Ext.create('HOR.store.StoreCentros', {});
		t.loadStoresAndThen(store_centros, function() {
			var store_estudios = Ext.create('HOR.store.StoreEstudios', {})			
			t.loadStoresAndThen(store_estudios, function() {
			var panel_filtro = Ext.create(
					'HOR.view.aulas.FiltroAsignacionAulas', {
						renderTo : Ext.getBody(),
						width : 800
					});
			});
		});

	});
});