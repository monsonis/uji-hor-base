Ext.define('HOR.controller.ControllerPrincipal',
{
    extend : 'Ext.app.Controller',
    views : 'ApplicationViewport',
    refs : [
    {
        selector : 'applicationViewportHorarios',
        ref : 'viewportHorarios'

    } ],
    init : function()
    {
        this.control(
        {
            'menuSuperior menuitem[action=gestion-horarios]' :
            {
                click : this.showGestionHorarios
            },
            'menuSuperior menuitem[action=curso-academico]' :
            {
                click : this.showCursoAcademico
            }
        });
    },

    showGestionHorarios : function()
    {
        this.getViewportHorarios().addNewTab('HOR.view.horarios.PanelHorarios');
    },
    
    showCursoAcademico: function()
    {
        this.getViewportHorarios().addNewTab('HOR.view.semestres.PanelSemestres');
    }
});