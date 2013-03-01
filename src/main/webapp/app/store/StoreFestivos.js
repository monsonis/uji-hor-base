Ext.define('HOR.store.StoreFestivos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.CalendarioAcademico',
    autoLoad : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/calendarioacademico/festivos',
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