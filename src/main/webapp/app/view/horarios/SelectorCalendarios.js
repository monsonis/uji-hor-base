Ext.define('HOR.view.horarios.SelectorCalendarios',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCalendarios',
    title : 'Calendaris',
    padding : 5,
    height : 250,
    layout :
    {
        type : 'vbox',
        align : 'stretch'
    },
    items : [
    {
        xtype : 'checkboxgroup',
        columns : 1,
        vertical : true,
        items : [
        {
            xtype : 'checkbox',
            boxLabel : 'Teoria',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '1'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Problemes',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '2'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Laboratoris',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '3'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Seminaris',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '4'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Tutories',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '5'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Avaluació',
            checked : true,
            padding : '10 10 0 10',
            name : 'calendario',
            inputValue : '6'
        }, ]
    } ],

    getCalendarsSelected : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;
        var calendarios = "";

        for ( var i = 0, len = checkboxes.length; i < len; i++)
        {
            if (checkboxes[i].getValue())
            {
                calendarios = calendarios + ";" + checkboxes[i].getSubmitValue();
            }
        }

        if (calendarios != '')
        {
            calendarios = calendarios.substring(1, calendarios.length);
        }

        return calendarios;
    }

});