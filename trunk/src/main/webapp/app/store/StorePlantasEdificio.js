Ext.define('HOR.store.StorePlantasEdificio',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.PlantaEdificio',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/edificio/planta',
        
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