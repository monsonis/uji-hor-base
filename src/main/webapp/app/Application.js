Ext.Loader.setConfig(
{
    enabled : true
});

Ext.Loader.setPath('Ext.ux', 'http://devel.uji.es/resources/js/extjs-4.1.0/examples/ux');
Ext.Loader.setPath('Ext.ux.uji', '/apa/Ext/ux/uji');

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
Ext.require('Ext.tree.Panel');
Ext.require('Ext.ux.TabCloseMenu');
Ext.require('Ext.ux.uji.TabPanel');
Ext.require('Ext.ux.uji.form.LookupComboBox');
Ext.require('Ext.ux.uji.form.LookupWindow');
Ext.require('Ext.form.field.Trigger');
Ext.require('Ext.form.field.Text');
Ext.require('Ext.form.Label');

var login = 'borillo';

Ext.application(
{
    name : 'APA',
    appFolder : 'app',
    autoCreateViewport : false,

    views : [ 'aplicacion.GridAplicaciones', 'cuenta.GridCuentas', 'dashboard.PanelDashboard' ],
    controllers : [ 'ControllerAplicaciones', 'ControllerCuentas', 'ControllerDashboards' ],

    launch : function()
    {
        Ext.create('Ext.ux.uji.ApplicationViewport',
        {
            codigoAplicacion : 'APA',
            tituloAplicacion : 'Aplicaciones, permisos y auditoría'
        });
    }
});