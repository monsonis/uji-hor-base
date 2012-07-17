Ext.define('HOR.view.horarios.SelectorHoras',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.selectorHoras',
    title : 'Hores del calendari',
    padding : 5,
    //height : 100,
    layout :
    {
        type : 'hbox',
        align : 'stretch'
    },

    comboHoraInicio : null,
    comboHoraFin : null,

    initComponent : function()
    {
        this.callParent(arguments);
        this.buildHoursCombos();
        this.add(this.comboHoraInicio);
        this.add(this.comboHoraFin);
    },

    initUI : function()
    {
        this.buildComboTitulacion();
        this.buildComboCurso();
        this.buildComboSemestre();
        this.buildComboGrupo();
    },

    buildHoursCombos : function()
    {
        var data = new Array();

        var j = 0;
        for ( var i = 8; i <= 22; i++)
        {
            var name = i + ':00';
            if ((i + '').length == 1)
            {
                name = '0' + name;
            }
            data[j++] = [ i, name ];
        }

        var hoursStore = Ext.create("Ext.data.ArrayStore",
        {
            fields : [ "id", "name" ],
            data : data
        });

        this.comboHoraInicio = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Hora inici',
            store : hoursStore,
            queryModel : 'local',
            editable : false,
            displayField : 'name',
            valueField : 'id',
            padding : '10 5 10 5',
            flex : 1,
        });

        this.comboHoraFin = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Hora fi',
            store : hoursStore,
            queryModel : 'local',
            editable : false,
            displayField : 'name',
            valueField : 'id',
            padding : '10 5 10 30',
            flex : 1,
        });
    }
});