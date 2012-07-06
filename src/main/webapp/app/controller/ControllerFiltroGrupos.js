Ext.define('HOR.controller.ControllerFiltroGrupos',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreEstudios', 'StoreCursos' ],
    model : [ 'Estudio', 'Curso' ],
    refs : [
    {
        selector : 'filtroGrupos',
        ref : 'filtroGrupos'
    } ],

    init : function()
    {
        this.control(
        {
            'filtroGrupos > #titulaciones' :
            {
                change : this.onTitulacionSelected
            },

            'filtroGrupos > #cursos' :
            {
                change : this.onCursoSelected
            }
        });
    },

    onTitulacionSelected : function(field, value)
    {
        this.getFiltroGrupos().down('#cursos').reset();
        this.getFiltroGrupos().down('#semestres').reset();
        this.getFiltroGrupos().down('#grupos').reset();

        var store = this.getStoreCursosStore();

        store.load(
        {
            params :
            {
                estudioId : value
            },
            scope : this
        });
    },

    onCursoSelected : function(field, value)
    {
        console.log("Curso " + value);
    }
});