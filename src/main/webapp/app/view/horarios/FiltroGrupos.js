Ext.define('HOR.view.horarios.FiltroGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroGrupos',

    border : false,
    padding : 5,
    closable : false,
    layout : 'anchor',

    items : [
    {
        xtype : 'combobox',
        fieldLabel : 'Titulació',
        labelWidth : 75,
        store : 'StoreEstudios',
        editable : false,
        displayField : 'nombre',
        valueField : 'id',
        name : 'estudio',
        anchor : '80%'
    },
    {
        xtype : 'panel',
        border : 0,
        anchor : '80%',
        layout :
        {
            type : 'hbox',
            align : 'fit'
        },
        defaults :
        {
            xtype : 'combobox',
            editable : false,
            lastQuery : '',
            width : 120,
            labelWidth : 75,
            labelAlign : 'left',
            margin : '0 20 0 0'
        },
        items : [
        {
            fieldLabel : 'Curs',
            store : 'StoreCursos',
            displayField : 'curso',
            valueField : 'curso',
            name : 'curso'
        },
        {
            fieldLabel : 'Semestre',
            store : 'StoreSemestres',
            displayField : 'semestre',
            valueField : 'semestre',
            name : 'semestre'
        },
        {
            fieldLabel : 'Grup',
            store : 'StoreGrupos',
            displayField : 'grupo',
            valueField : 'grupo',
            name : 'grupo'
        },
        {
            margin : '0 0 0 0',
            name : 'intervaloHorario',
            xtype : 'button',
            hidden : true,
            width : '40',
            text : 'Interval horari...'
        },
        {
            margin : '0 0 0 10',
            name : 'calendarioDetalle',
            xtype : 'button',
            enableToggle : true,
            hidden : true,
            width : '40',
            flex : 0,
            text : 'Setmana detallada',
            listeners :
            {
                click : function()
                {
                    if (!this.pressed)
                    {
                        this.toggle();
                    }
                    var otherButton = Ext.ComponentQuery.query("button[name=calendarioGenerica]")[0];
                    if (otherButton.pressed)
                    {
                        otherButton.toggle();
                    }
                }
            }
        },
        {
            margin : '0 0 0 0',
            name : 'calendarioGenerica',
            xtype : 'button',
            enableToggle : true,
            pressed : true,
            hidden : true,
            margin: '0 0 0 5',
            width : '40',
            flex : 0,
            text : 'Setmana genèrica',
            listeners :
            {
                click : function()
                {
                    if (!this.pressed)
                    {
                        this.toggle();
                    }
                    var otherButton = Ext.ComponentQuery.query("button[name=calendarioDetalle]")[0];
                    if (otherButton.pressed)
                    {
                        otherButton.toggle();
                    }
                }
            }
        } ]
    } ]
});