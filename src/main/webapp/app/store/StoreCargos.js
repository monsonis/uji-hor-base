Ext.define('HOR.store.StoreCargos',
{   
           
 	extend : 'Ext.data.Store',
    model : 'HOR.model.Cargo',
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/persona/cargos',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        writer :
        {
            type : 'json'
        }
    }	 
	    

});