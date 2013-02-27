Ext.require('Ext.tree.TreePanel');

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
            height : 70,
            items : [
            {
                region : 'center',
                style : 'border:0px',
                html : '<div style="background: url(http://e-ujier.uji.es/img/portal2/imagenes/cabecera_1px.png) repeat-x scroll left top transparent; height: 70px;">'
                        + '<img src="http://e-ujier.uji.es/img/portal2/imagenes/logo_uji_horizontal.png" style="float: left;margin: 10px 16px;" />' + '<div style="float:left; margin-top:11px;">'
                        + '<span style="color: rgb(255,255, 255); font-family: Helvetica,Arial,sans-serif;font-size:1.2em;">E-UJIER@</span><br/>'
                        + '<span style="color: #CDCCE5; font-family: Helvetica,Arial,sans-serif;">' + this.tituloAplicacion + '</span></div></div>'
            } ]
        });
    },

    buildNavigationTree : function()
    {
        var navigationTree = new Ext.tree.TreePanel(
        {
            title : 'Conectat com ' + login + '@',
            region : 'west',
            alias : 'widget.navigationtree',
            lines : false,
            width : this.treeWidth,
            split : true,
            collapsible : true,
            autoScroll : true,
            rootVisible : false,
            bodyStyle : 'padding-bottom:20px;',

            store : Ext.create('Ext.data.TreeStore',
            {
                expanded : true,
                autoLoad : true,
                proxy :
                {
                    type : 'ajax',
                    url : '/hor/rest/navigation/class?codigoAplicacion=HOR',
                    noCache : true,

                    reader :
                    {
                        type : 'json',
                        root : 'row'
                    }
                }
            }),

        });

        navigationTree.getRootNode().expand();
        this.add(navigationTree);
    }
});