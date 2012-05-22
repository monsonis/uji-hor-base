Ext.define('APA.view.cuenta.GridCuentas', {
   extend : 'Ext.grid.Panel',
   alias : 'widget.gridCuentas',
   requires : [ 'Ext.grid.plugin.RowEditing' ],

   title : 'Cuentas',
   store : 'StoreCuentas',
   closable : true,

   initComponent : function() {

      this.columns = [ {
         header : 'Id',
         dataIndex : 'id',
         hidden : true
      },{
         header : 'Codi',
         dataIndex : 'personaId',
         flex : 1,
         field : {
            xtype : 'lookupCombobox',
            appPrefix : 'apa',
            bean : 'persona',
            allowBlank : false
         }
      }, {
         header : 'Nom d\'usuari',
         dataIndex : 'cuenta',
         flex : 10,
         field : {
            xtype : 'textfield',
            allowBlank : false
         }
      } ];

      this.plugins = [ {
         ptype : 'rowediting',
         clicksToEdit : 2
      } ];

      this.tbar = [ {
         xtype : 'button',
         text : 'Afegir',
         action : 'add'
      }, {
         xtype : 'button',
         text : 'Esborrar',
         action : 'remove'
      } ];

      this.callParent(arguments);
   },

   addEmptyRowAndEdit : function() {
      var rowEditor = this.getPlugin();
      var record = Ext.create('APA.model.Cuenta', {});

      rowEditor.cancelEdit();
      this.store.insert(0, record);
      rowEditor.startEdit(0, 0);
   },

   removeSelectedRow : function() {
      var rowEditor = this.getPlugin();
      var records = this.getSelectionModel().getSelection();
      
      rowEditor.cancelEdit();
      records['id'] = records['personaId'];
      
      this.store.remove(records);
   }
});