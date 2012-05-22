Ext.define('APA.controller.ControllerCuentas', {
   extend : 'Ext.app.Controller',
   stores : [ 'StoreCuentas' ],
   model : [ 'Cuenta' ],
   views : [ 'cuenta.GridCuentas' ],

   init : function() {
      this.control({
         'gridCuentas button[action=add]' : {
            click : this.addCuenta
         },
         
         'gridCuentas button[action=remove]' : {
            click : this.removeCuenta
         }
      });
   },

   addCuenta : function(button, event, opts) {
      button.up("gridCuentas").addEmptyRowAndEdit();
   },
   
   removeCuenta : function(button, event, opts) {
      button.up("gridCuentas").removeSelectedRow();
   }   
});