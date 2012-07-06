Ext.define('HOR.controller.ControllerGrupoAsignatura',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreGruposAsignaturasSinAsignar' ],
    model : [ 'GrupoAsignatura' ],
    refs : [
    {
        selector : 'selectorGrupos',
        ref : 'selectorGruposView'
    } ],

    init : function()
    {
        this.control(
        {
            'selectorGrupos button[action=cargar]' :
            {
                click : this.onFilterSelected
            }
        });
    },

    onFilterSelected : function()
    {
        var store = this.getStoreGruposAsignaturasSinAsignarStore();

        store.load(
        {
            callback : this.onGruposAsignaturasSinAsignarLoaded,
            params :
            {
                estudioId : '224',
                cursoId : '1',
                semestreId : '1',
                grupoId : 'A'
            },
            scope : this
        });
    },

    onGruposAsignaturasSinAsignarLoaded : function(gruposAsignaturas, request)
    {
        var view = this.getSelectorGruposView();
        var store = this.getStoreGruposAsignaturasSinAsignarStore();

        store.each(function()
        {
            var button =
            {
                xtype : 'button',
                text : this.data.titulo,
                padding : 5,
                margin : '5 30 0 30',
                grupoAsignaturaId : this.data.id
            };

            view.add(button);
        });
    }
});