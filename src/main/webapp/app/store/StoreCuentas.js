Ext.define('APA.store.StoreCuentas', {
	extend : 'Ext.data.Store',
	model : 'APA.model.Cuenta',

	autoLoad : true,
	autoSync : true,

	proxy : {
		type : 'rest',
		url : '/apa/rest/cuenta',

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
