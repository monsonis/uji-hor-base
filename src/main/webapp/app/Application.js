Ext.Loader.setConfig(
{
    enabled : true,
    paths :
    {
        'Ext.ux' : '/hor/examples/ux',
        'Ext.ux.uji' : '/hor/Ext/ux/uji',
        'Extensible' : '/hor/extensible-1.5.1/src',
        'Event' : '/hor/app/event'
    }
});

Ext.require('Ext.data.proxy.Rest');
Ext.require('Ext.data.reader.Xml');
Ext.require('Ext.data.reader.Json');
Ext.require('Ext.data.writer.Xml');
Ext.require('Ext.data.writer.Json');
Ext.require('Ext.data.TreeStore');
Ext.require('Ext.container.Viewport');
Ext.require('Ext.layout.container.Border');
Ext.require('Ext.tab.Panel');
Ext.require('Ext.tree.Panel');
Ext.require('Ext.ux.TabCloseMenu');
Ext.require('Ext.ux.uji.TabPanel');
Ext.require('Ext.ux.uji.form.LookupComboBox');
Ext.require('Ext.ux.uji.form.LookupWindow');
Ext.require('Ext.ux.uji.ApplicationViewport');
Ext.require('Ext.form.field.Trigger');
Ext.require('Ext.form.FieldSet');
Ext.require('Ext.form.field.Text');
Ext.require('Ext.form.Label');
Ext.require('Ext.toolbar.Toolbar');
Ext.require('Ext.toolbar.Spacer');
Ext.require('Extensible.calendar.menu.Event');
Ext.require('Extensible.calendar.data.MemoryCalendarStore');
Ext.require('Extensible.calendar.data.EventMappings');
Ext.require('Extensible.calendar.data.EventStore');
Ext.require('Extensible.calendar.CalendarPanel');
Ext.require('Extensible.calendar.template.BoxLayout');
Ext.require('Event.form.field.DateRange');
Ext.require('Ext.form.field.Checkbox');
Ext.require('Ext.form.field.Radio');
Ext.require('Ext.form.field.Hidden');
Ext.require('Ext.form.RadioGroup');
Ext.require('Event.form.field.RadioNumber');
Ext.require('Event.form.field.RadioDate');
Ext.require('Event.form.field.DetalleManual');
Ext.require('Ext.layout.container.Table');
Ext.require('Event.form.DetalleClases');
Ext.require('Ext.grid.Panel');
Ext.require('Ext.Date');


var login = 'borillo';

function fixLoadMaskBug(store, combo)
{
    store.on('load', function(store, records, successful, options)
    {
        if (successful && Ext.typeOf(combo.getPicker().loadMask) !== "boolean")
        {
            combo.getPicker().loadMask.hide();
        }
    });
}

Ext.application(
{
    name : 'HOR',
    appFolder : 'app',
    autoCreateViewport : false,

    views : [ 'dashboard.PanelDashboard', 'horarios.PanelHorarios', 'horarios.FiltroGrupos', 'horarios.PanelCalendario', 'horarios.SelectorIntervaloHorario', 'commons.MenuSuperior', 'semestres.PanelSemestres', 
            'ApplicationViewport' ],
    controllers : [ 'ControllerDashboards', 'ControllerCalendario', 'ControllerGrupoAsignatura', 'ControllerFiltroCalendario', 'ControllerConfiguracion', 'ControllerPrincipal', 'ControllerSemestreDetalle', 'ControllerFiltroAsignacionAulas' ],

    launch : function()
    {
        Ext.Loader.loadScriptFile('/hor/app/config/calendar.js', function()
        {
            var viewport = Ext.create('HOR.view.ApplicationViewport',
            {
                codigoAplicacion : 'HOR',
                tituloAplicacion : 'Gestión de horarios',
                dashboard : true
            });
            viewport.addNewTab('HOR.view.horarios.PanelHorarios');
        });
    }

});