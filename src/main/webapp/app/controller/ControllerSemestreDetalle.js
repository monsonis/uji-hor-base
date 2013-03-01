Ext.define('HOR.controller.ControllerSemestreDetalle',
{
    extend : 'Ext.app.Controller',
    stores : [ 'StoreSemestreDetalles', 'StoreFestivos' ],
    model : [ 'SemestreDetalle', 'CalendarioAcademico' ],
    views : [ 'semestres.PanelSemestres', 'semestres.GridSemestres', 'semestres.GridFestivos' ],
    refs : [
    {
        selector : 'gridFestivos',
        ref : 'gridFestivos'
    } ],

    init : function()
    {
        this.control(
        {
            'gridSemestres' :
            {
                select : function(panel, registro, indice)
                {
                    this.onSemestreSelected(panel, registro);
                }
            }
        });
    },

    onSemestreSelected : function(panel, registro)
    {
        var fechaInicio = Ext.Date.format(registro.data.fechaInicio, "d/m/Y");
        var fechaFin = Ext.Date.format(registro.data.fechaExamenesFin, "d/m/Y");

        var storeFestivos = this.getGridFestivos().getStore();
        storeFestivos.load(
        {
            params :
            {
                fechaInicio : fechaInicio,
                fechaFin : fechaFin
            }
        });
    }
});