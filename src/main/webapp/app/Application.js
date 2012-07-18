Ext.Loader.setConfig(
{
    enabled : true,
    paths :
    {
        'Ext.ux' : '/hor/examples/ux',
        'Ext.ux.uji' : '/hor/Ext/ux/uji',
        'Extensible' : '/hor/extensible-1.5.1/src'
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
Ext.require('Ext.form.field.Trigger');
Ext.require('Ext.form.field.Text');
Ext.require('Ext.form.Label');
Ext.require('Extensible.calendar.data.MemoryCalendarStore');
Ext.require('Extensible.calendar.data.EventStore');
Ext.require('Extensible.calendar.CalendarPanel');

var login = 'ferrerq';

Ext.application(
{
    name : 'HOR',
    appFolder : 'app',
    autoCreateViewport : false,

    views : [ 'dashboard.PanelDashboard', 'horarios.PanelHorarios', 'commons.FiltroGrupos', 'horarios.PanelCalendario', 'configuracion.PanelConfiguracion' ],
    controllers : [ 'ControllerDashboards', 'ControllerCalendario', 'ControllerGrupoAsignatura', 'ControllerFiltroConfiguracion', 'ControllerFiltroCalendario', 'ControllerConfiguracion' ],

    launch : function()
    {
        Ext.Loader.loadScriptFile('/hor/extensible-1.5.1/examples/calendar/ext-locales/ext-lang-ca.js', function()
        {

            Ext.Loader.loadScriptFile('/hor/js/extensible-lang-ca.js', function()
            {
                Ext.create('Ext.ux.uji.ApplicationViewport',
                {
                    codigoAplicacion : 'HOR',
                    tituloAplicacion : 'Gestión de horarios'
                });
            });
        });
    }

});