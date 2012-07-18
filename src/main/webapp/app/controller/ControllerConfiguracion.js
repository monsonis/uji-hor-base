Ext.define('HOR.controller.ControllerConfiguracion',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreHoras' ],
    ref : [
    {
        selector : 'panelConfiguracion',
        ref : 'panelConfiguracion'
    },
    {
        selector : 'panelHorarios filtroGrupos',
        ref : 'filtroGrupos'
    },
    {
        selector : 'panelCalendario',
        ref : 'panelCalendario'
    },
    {
        selector : 'selectorCalendarios',
        ref : 'selectorCalendarios'
    } ],

    init : function()
    {
        this.control(
        {
            /*
             * 'panelConfiguracion filtroGrupos combobox[name=grupo]' : { select : function() {
             * console.log(this.getStoreHorasStore()); } }
             */
            'panelConfiguracion button[text=Guardar]' :
            {
                click : this.guardarConfiguracion
            }
        });
    },
    guardarConfiguracion : function()
    {
        var titulaciones = this.getFiltroGrupos().down('combobox[name=estudio]');
        var cursos = this.getFiltroGrupos().down('combobox[name=curso]');
        var semestres = this.getFiltroGrupos().down('combobox[name=semestre]');
        var grupos = this.getFiltroGrupos().down('combobox[name=grupo]');

        var estudioId = titulaciones.getValue();

        console.log(estudioId);
    }
});