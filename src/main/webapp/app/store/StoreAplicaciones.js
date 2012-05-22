Ext.define('APA.store.StoreAplicaciones', {
	extend : 'Ext.data.Store',
	model : 'APA.model.Aplicacion',

	autoLoad : true,
	autoSync : true,

	proxy : {
		type : 'rest',
		url : '/apa/rest/aplicacion',

		reader : {
			type : 'json',
			successProperty: 'success',
			root : 'data'
		},

		writer : {
			type : 'json'
		}
	}
});