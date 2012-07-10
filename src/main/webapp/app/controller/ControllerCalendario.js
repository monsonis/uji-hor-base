Ext.define('HOR.controller.ControllerCalendario',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreCalendarios', 'StoreEventos' ],
    model : [ 'Calendario', 'Evento' ],
    refs : [
    {
        selector : 'filtroGrupos',
        ref : 'filtroGrupos'
    } ],

    init : function()
    {
        this.control(
        {
            'filtroGrupos > #grupos' :
            {
                select : this.onFilterSelected
            }
        });

    },

    onFilterSelected : function(combo, records)
    {
        var grupo = records[0].get('grupo');
        var titulaciones = this.getFiltroGrupos().down('#titulaciones');
        var cursos = this.getFiltroGrupos().down('#cursos');
        var semestres = this.getFiltroGrupos().down('#semestres');
        var storeEventos = this.getStoreEventosStore();
        storeEventos.load();
//        storeEventos.load(
//        {
//            params :
//            {
//                estudioId : titulaciones.getValue(),
//                cursoId : cursos.getValue(),
//                semestreId : semestres.getValue(),
//                grupoId : grupo
//            },
//            scope : this
//        });
    }

});