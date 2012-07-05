Ext.ns('Ext.ux.uji');

Ext.ux.uji.DatePickerPanel = Ext.extend(Ext.Panel, {
   version: "0.0.1", 
   dateList : {},
   datePicker : {},
   layout : 'hbox',
   getDateList : function() {
      return this.dateList;
   },
   getDatePicker : function() {
      return this.datePicker;
   },
   initComponent : function() {
      Ext.ux.uji.DatePickerPanel.superclass.initComponent.call(this);

      this.dateList = new Ext.ux.uji.grid.OneColumnGrid( {
         dataColumnTitle : 'Seleccionadas',
         autoScroll : true,
         width : 130,
         height : 155
      });

      var dateListComponent = this.dateList;

      this.datePicker = new Ext.DatePicker( {
         autoWidth : true,
         startDay : 1,
         listeners : {
            'select' : function(element, date) {
               var posicion = dateListComponent.store.find("data", date.format('d/m/Y'));
               if (posicion == -1) dateListComponent.store.loadData( [ [ date.format('d/m/Y') ] ], true);
            }
         }
      });

      this.add(this.datePicker);
      this.add( {
         xtype : 'panel',
         layout : 'vbox',
         width : 140,
         height : 195,
         padding : 5,
         items : [ {
            xtype : 'panel',
            layout : 'hbox',
            width : 140,
            height : 28,
            align : 'middle',
            items : [ {
               xtype : 'button',
               text : 'Eliminar',
               handler : function(item, event) {
                  var lista = dateListComponent.getSelectionModel().getSelections();

                  for ( var i = 0; i < lista.length; i++) {
                     dateListComponent.store.remove(lista[i]);
                  }
               }
            }, {
               xtype : 'button',
               text : 'Limpiar',
               style : 'margin-left: 10px;',
               handler : function(item, event) {
                  dateListComponent.store.removeAll();
               }
            } ]
         }, dateListComponent ]
      });
   }
});