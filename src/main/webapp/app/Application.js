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
Ext.require('Ext.form.FieldSet');
Ext.require('Ext.form.field.Text');
Ext.require('Ext.form.Label');
Ext.require('Extensible.calendar.menu.Event');
Ext.require('Extensible.calendar.data.MemoryCalendarStore');
Ext.require('Extensible.calendar.data.EventStore');
Ext.require('Extensible.calendar.CalendarPanel');
Ext.require('Extensible.calendar.template.BoxLayout');

var login = 'ferrerq';

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

    views : [ 'dashboard.PanelDashboard', 'horarios.PanelHorarios', 'horarios.FiltroGrupos', 'horarios.PanelCalendario', 'horarios.SelectorIntervaloHorario' ],
    controllers : [ 'ControllerDashboards', 'ControllerCalendario', 'ControllerGrupoAsignatura', 'ControllerFiltroCalendario', 'ControllerConfiguracion' ],

    launch : function()
    {
        Ext.Loader.loadScriptFile('/hor/app/config/ext-lang-ca.js', function()
        {

            Ext.Loader.loadScriptFile('/hor/app/config/extensible-lang-ca.js', function()
            {
                Ext.Loader.loadScriptFile('/hor/app/config/calendar.js', function()
                {
                    Ext.create('Ext.ux.uji.ApplicationViewport',
                    {
                        codigoAplicacion : 'HOR',
                        tituloAplicacion : 'Gestión de horarios'
                    });
                });
            });
        });
    }

});