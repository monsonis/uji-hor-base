Ext.define('HOR.view.circuitos.SelectorCalendariosCircuitos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorCalendariosCircuitos',
    title : 'Filtres',
    padding : 5,
    height : 120,
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
            boxLabel : 'En circuit',
            checked : true,
            padding : '10 10 0 10',
            name : 'encircuito',
            inputValue : '1'
        },
        {
            xtype : 'checkbox',
            boxLabel : 'Fora de circuit',
            checked : true,
            padding : '10 10 0 10',
            name : 'fueracircuito',
            inputValue : '2'
        } ]
    } ],

    getCalendarsSelected : function()
    {
        var checkboxes = this.down('checkboxgroup').items.items;
        var calendarios = [];

        for ( var i = 0, len = checkboxes.length; i < len; i++)
        {
            if (checkboxes[i].getValue())
            {
                calendarios.push(checkboxes[i].getSubmitValue());
            }
        }

        return calendarios.join(';');
    }

});