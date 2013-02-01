Ext.define('HOR.model.AulaPlanificacion',
{
    extend : 'Ext.data.Model',

    fields : [ 'id', 'nombre', 'estudioId', 'semestreId', 'aulaId', 'edificio', 'tipo', 'planta' ]
});