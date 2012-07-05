Ext.ns('Ext.ux.uji.form');

Ext.ux.uji.form.StaticComboBox = Ext.extend(Ext.form.ComboBox, {
    version: "0.0.1",
    mode : 'local',
   emptyText : 'trieu...',
   triggerAction : 'all',
   forceSelection : true,
   editable : false,
   displayField : 'nombre',
   valueField : 'id',
   staticData : [],

   initComponent : function() {
      var config = {};
      Ext.apply(this, Ext.apply(this.initialConfig, config));
      
      Ext.ux.uji.form.StaticComboBox.superclass.initComponent.call(this);

      this.store = new Ext.data.SimpleStore({
         fields : [ 'id', 'nombre' ],
         data : []
      });

      this.store.loadData(this.staticData);

   }
});
