Ext.define('HOR.view.circuitos.VentanaEdicionCircuitos',
{
    extend : 'Ext.Window',
    title : 'Edició de circuits',
    alias : 'widget.ventanaEdicionCircuitos',
    layout : 'fit',
    modal : true,

    buttonAlign : 'right',
    bbar : [
    {
        xtype : 'tbfill'
    },
    {
        xtype : 'button',
        text : 'Guardar',
        action : 'guardar'
    },
    {
        xtype : 'button',
        text : 'Cancel·lar',
        action : 'cancelar'
    } ],

    items : [
    {
        xtype : 'form',
        name : 'formEdicionCircuitos',
        padding : 10,
        items : [
        {
            xtype : 'textfield',
            fieldLabel : 'Nom circuit',
            name : 'nombre',
            allowBlank : false,
            padding : 10,
            width : 500
        },
        {
            xtype : 'numberfield',
            fieldLabel : 'Nombre places',
            name : 'plazas',
            allowBlank : false,
            padding : 10,
            minValue : 0
        },
        {
            xtype : 'hidden',
            name : 'id_circuito'
        } ]
    } ]
});