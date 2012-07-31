Ext.require('HOR.view.commons.MenuSuperior');

Ext.define('HOR.view.ApplicationViewport',
{
    extend : 'Ext.ux.uji.ApplicationViewport',
    alias : 'widget.applicationViewportHorarios',

    buildLogoPanel : function()
    {
        this.add(
        {
            region : 'north',
            layout : 'border',
            height : 100,
            items : [
                    {
                        region : 'center',
                        style : 'border:0px',
                        html : '<div style="background: url(http://e-ujier.uji.es/img/portal2/imagenes/cabecera_1px.png) repeat-x scroll left top transparent; height: 70px;">'
                                + '<img src="http://e-ujier.uji.es/img/portal2/imagenes/logo_uji_horizontal.png" style="float: left;margin: 10px 16px;" />'
                                + '<div style="float:left; margin-top:11px;">'
                                + '<span style="color: rgb(255,255, 255); font-family: Helvetica,Arial,sans-serif;font-size:1.2em;">E-UJIER@</span><br/>'
                                + '<span style="color: #CDCCE5; font-family: Helvetica,Arial,sans-serif;">' + this.tituloAplicacion + '</span></div></div>'
                    },
                    {
                        region : 'south',
                        xtype : 'menuSuperior'
                    } ]
        });
    },

    buildNavigationTree : function()
    {
    }
});