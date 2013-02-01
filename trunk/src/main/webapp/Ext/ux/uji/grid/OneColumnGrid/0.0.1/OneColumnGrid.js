Ext.ns('Ext.ux.uji.grid');

Ext.ux.uji.grid.OneColumnGrid = Ext.extend(Ext.grid.GridPanel, {
   version: "0.0.1",
   dataColumnTitle: 'Data',
   store: new Ext.data.ArrayStore({
      fields: [ 'data' ]
   }),
   columns: [{ 
      name: 'data',
      dataIndex: 'data',
      sortable: true
   }],    
   initComponent : function() {
      Ext.ux.uji.grid.OneColumnGrid.superclass.initComponent.call(this);
      
      this.columns[0].header = this.dataColumnTitle;
      this.columns[0].width = this.width-30;
   }    
});