Ext.define('APA.controller.ControllerAplicaciones', {
   extend : 'Ext.app.Controller',
   stores : [ 'StoreAplicaciones' ],
   model : [ 'Aplicacion' ],
   views : [ 'aplicacion.GridAplicaciones' ],

   init : function() {
      this.control({
         'gridAplicaciones button[action=add]' : {
            click : this.addAplicacion
         },
         
         'gridAplicaciones button[action=remove]' : {
            click : this.removeAplicacion
         }
      });
   },

   addAplicacion : function(button, event, opts) {
      button.up("gridAplicaciones").addEmptyRowAndEdit();
   },
   
   removeAplicacion : function(button, event, opts) {
      button.up("gridAplicaciones").removeSelectedRow();
   }   
});