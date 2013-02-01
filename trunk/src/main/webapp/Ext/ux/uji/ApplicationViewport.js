Ext.require('Ext.container.Viewport');
Ext.require('Ext.layout.container.Border');

Ext.define('Ext.ux.uji.ApplicationViewport', {
   extend : 'Ext.Viewport',
   alias : 'widget.applicationViewport',

   layout : 'border',
   treeWidth : 235,
   aplicacionCompleta : true,
   tabPanel : {},

   initComponent : function() {
      this.callParent(arguments);

      if (this.aplicacionCompleta) {
         this.buildLogoPanel();
         this.buildNavigationTree();
         this.buildWorkTabPanel();
      }
   },

   buildLogoPanel : function() {
      var logoPanel = new Ext.Panel({
         region : 'north',
         style : 'border:0px',
         html : '<div style="background: url(http://e-ujier.uji.es/img/portal2/imagenes/cabecera_1px.png) repeat-x scroll left top transparent; height: 70px;">'
               + '<img src="http://e-ujier.uji.es/img/portal2/imagenes/logo_uji_horizontal.png" style="float: left;margin: 10px 16px;" />' + '<div style="float:left; margin-top:11px;">'
               + '<span style="color: rgb(255,255, 255); font-family: Helvetica,Arial,sans-serif;font-size:1.2em;">E-UJIER@</span><br/>'
               + '<span style="color: #CDCCE5; font-family: Helvetica,Arial,sans-serif;">' + this.tituloAplicacion + '</span></div></div>'
      });

      this.add(logoPanel);
   },

   buildNavigationTree : function() {
      var me = this;

      var navigationTree = Ext.create('Ext.tree.Panel', {
         title : 'Conectat com ' + login + '@',
         region : 'west',
         lines : false,
         width : this.treeWidth,
         split : true,
         collapsible : true,
         autoScroll : true,
         rootVisible : false,
         bodyStyle : 'padding-bottom:20px;',
         store : Ext.create('Ext.data.TreeStore', {
            autoLoad : true,

            root : {
               expanded : true
            },

            proxy : {
               type : 'ajax',
               url : '/' + me.codigoAplicacion.toLowerCase() + '/rest/navigation/json/class?codigoAplicacion=' + this.codigoAplicacion,
               reader : {
                  type : 'json',
                  root : 'row'
               }
            }
         })
      });

      this.add(navigationTree);
   },

   buildWorkTabPanel : function() {
      this.tabPanel = Ext.create('Ext.ux.uji.TabPanel', {
         deferredRender : false,
         region : 'center'
      });

      if (!this.dashboard) {
         eval('this.tabPanel.addTab(Ext.create("' + this.codigoAplicacion + '.view.dashboard.PanelDashboard", {}));');
      }

      this.add(this.tabPanel);
   },

   addNewTab : function(id) {
      var newPanel = eval("Ext.create('" + id + "', {})");
      this.tabPanel.addTab(newPanel);

      newPanel.on('newtab', function(config) {
         if (config && config.pantalla) {
            var params = Ext.util.JSON.encode(config || {});
            var newPanel = eval("Ext.create('" + config.pantalla + "', " + params + ")");
            this.tabPanel.addTab(newPanel);
         } else {
            alert('[ApplicationViewport.js] ¡Atención!.' + 'El parámetro "pantalla" (newtab) con' + 'el nombre del componente que se quiere' + 'instanciar debe estar definido. Por ejemplo:'
                  + '"pantalla : \'UJI.BEC.GestionBecaPanel\'"');
         }
      }, this);
   }
});
