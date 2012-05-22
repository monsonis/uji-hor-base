Ext.define('APA.view.aplicacion.GridAplicaciones', {
   extend : 'Ext.grid.Panel',
   alias : 'widget.gridAplicaciones',
   requires : [ 'Ext.grid.plugin.RowEditing' ],

   title : 'Aplicaciones',
   store : 'StoreAplicaciones',
   closable : true,

   initComponent : function() {

      this.columns = [ {
         header : 'Codi',
         dataIndex : 'id',
         flex : 5
      }, {
         header : 'Codi app',
         dataIndex : 'codigo',
         flex : 10,
         field : {
            xtype : 'textfield',
            allowBlank : false
         }
      }, {
         header : 'Nom',
         dataIndex : 'nombre',
         flex : 40,
         field : {
            xtype : 'textfield',
            allowBlank : false
         }
      }, {
         header : 'Adreça',
         dataIndex : 'url',
         flex : 40,
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
      var record = Ext.create('APA.model.Aplicacion', {});

      rowEditor.cancelEdit();
      this.store.insert(0, record);
      rowEditor.startEdit(0, 0);
   },

   removeSelectedRow : function() {
      var rowEditor = this.getPlugin();
      var records = this.getSelectionModel().getSelection();

      rowEditor.cancelEdit();
      this.store.remove(records);
   }
});