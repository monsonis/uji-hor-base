Ext.define('HOR.model.AulaPlanificacion',
{
    extend : 'Ext.data.Model',

    fields : [ 'id', 'nombre', 'estudioId', 'semestreId', 'aulaId', 'edificio', 'tipo', 'planta', 'plazas',
    {
        name : 'nombreYPlazas',
        mapping : 'nombre',
        convert : function(v, record)
        {
            return v + " (" + record.data.plazas + " plaçes)";
        }
    } ]
});