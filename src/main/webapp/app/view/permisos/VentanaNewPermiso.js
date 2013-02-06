Ext.define('HOR.view.permisos.VentanaNewPermiso',
{
    extend : 'Ext.Window',
    title : 'Afegir un nou permís',
    width : 800,
    alias : 'widget.ventanaNewPermiso',
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
        action : 'guardar-permiso'
    },
    {
        xtype : 'button',
        text : 'Cancel·lar',
        action : 'cancelar'
    } ],

    items : [
    {
        xtype : 'form',
        name : 'formNewPermiso',
        padding : 10,
        items : [
        {
            fieldLabel : 'Sel·lecciona la persona',
            xtype : 'lookupCombobox',
            appPrefix : 'hor',
            bean: 'persona',
            name : 'comboPersona',
            padding : 20,
            fieldLabel : 'Persona',
            labelWidth : 75,
            editable : false,
            allowBlank : false,
            displayField : 'nombre',
            valueField : 'id',
            anchor : '100%'
        },
        {
            xtype : 'combo',
            name : 'comboTitulacion',
            fieldLabel : 'Sel·lecciona la titulació',
            padding : '0 20 20 20',
            fieldLabel : 'Titulació',
            labelWidth : 75,
            store : 'StoreEstudios',
            editable : false,
            displayField : 'nombre',
            valueField : 'id',
            allowBlank : false,
            anchor : '100%'
        },

        {
            xtype : 'combo',
            name : 'comboCargo',
            fieldLabel : 'Sel·lecciona el càrrec',
            padding : '0 20 20 20',
            fieldLabel : 'Càrrec',
            labelWidth : 75,
            store : 'StoreCargos',
            editable : false,
            displayField : 'nombre',
            valueField : 'id',
            allowBlank : false,
            anchor : '100%'
        } ]
    } ]

});