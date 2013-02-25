Ext.define('HOR.view.aulas.calendar.FiltroAulas',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroAulas',

    border : false,
    padding : 5,
    closable : false,
    layout : 'anchor',

    items : [
    {
        xtype : 'panel',
        border : 0,
        layout :
        {
            type : 'hbox',
            align : 'stretch'
        },
        defaults :
        {
            xtype : 'combobox',
            editable : false,
            labelWidth : 60,
            labelAlign : 'left',
            margin : '0 20 0 0',
            lastQuery : ''
        },
        items : [
        {
            fieldLabel : 'Centre',
            store : 'StoreCentros',
            displayField : 'nombre',
            valueField : 'id',
            name : 'centro',
            width : 500,
        },
        {
            fieldLabel : 'Semestre',
            store : 'StoreSemestresAulas',
            displayField : 'nombre',
            valueField : 'semestre',
            name : 'semestre',
            width : 120,
        } ]
    },
    {
        xtype : 'panel',
        border : 0,
        anchor : '100%',
        layout :
        {
            type : 'hbox',
            align : 'fit',
        },

        items : [
        {
            xtype : 'panel',
            border : 0,
            anchor : '70%',
            layout :
            {
                type : 'hbox',
                align : 'fit',
                padding : '5 0 0 0'
            },
            defaults :
            {
                xtype : 'combobox',
                editable : false,
                lastQuery : '',
                labelWidth : 60,
                labelAlign : 'left',
                margin : '0 20 0 0'
            },
            items : [
            {
                fieldLabel : 'Edifici',
                width : 120,
                matchFieldWidth : false,
                listConfig :
                {
                    width : 180
                },
                store : 'StoreEdificios',
                displayField : 'nombre',
                valueField : 'nombre',
                name : 'edificio',
            },
            {
                fieldLabel : 'Tipus aula',
                width : 170,
                labelWidth : 90,
                matchFieldWidth : false,
                listConfig :
                {
                    width : 180
                },
                store : 'StoreTiposAula',
                displayField : 'nombre',
                valueField : 'valor',
                name : 'tipoAula',
            },
            {
                fieldLabel : 'Planta',
                width : 170,
                matchFieldWidth : false,
                listConfig :
                {
                    width : 180
                },
                store : 'StorePlantasEdificio',
                displayField : 'nombre',
                valueField : 'valor',
                name : 'planta'
            } ]
        },
        {
            xtype : 'panel',
            border : 0,
            anchor : '50%',
            flex : 1,
            layout :
            {
                type : 'hbox',
                align : 'fit',
                pack : 'end'
            },
            defaults :
            {
                width : 120,
                labelWidth : 75,
                labelAlign : 'left',
                margin : '0 20 0 0'
            },
            items : [
            {
                margin : '0 0 0 0',
                name : 'imprimir',
                xtype : 'button',
                hidden : true,
                margin : '0 0 0 5',
                width : '40',
                flex : 0,
                text : 'Imprimir',
                iconCls : 'printer'
            },
            {
                margin : '0 0 0 10',
                name : 'calendarioAulasDetalle',
                xtype : 'button',
                enableToggle : true,
                hidden : true,
                width : '40',
                flex : 0,
                text : 'Set. detallada',
                iconCls : 'calendar-week'
            },
            {
                margin : '0 0 0 10',
                name : 'calendarioAulasGenerica',
                xtype : 'button',
                enableToggle : true,
                pressed : true,
                hidden : true,
                margin : '0 0 0 5',
                width : '40',
                flex : 0,
                text : 'Set. genèrica',
                iconCls : 'calendar-edit'
            } ]
        } ]
    } ]

});