Ext.define('Ext.ux.uji.TabPanel', {
   extend : 'Ext.TabPanel',
   alias : 'widget.tabPanel',
   
   version : "0.0.1",
   activeTab : 0,
   margins : '0 5 5 0',
   tabWidth : 150,
   minTabWidth : 120,
   enableTabScroll : true,
   deferredRender : false,
   autoShow : true,
   frame : false,
   plain : true,
   border : true,
   plugins : [ {
      ptype : 'tabclosemenu'
   } ],

   initComponent : function() {
      this.callParent(arguments);
   },

   addTab : function(panel) {
      var tabs = this.down('panel[title="' + panel.title + '"]');

      if (tabs) {
         this.remove(tabs, true);
      }

      this.add(panel);
      this.setActiveTab(panel.id);
      
      this.doLayout();
   }
});