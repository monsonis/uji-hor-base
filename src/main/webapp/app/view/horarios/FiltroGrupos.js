Ext.define('HOR.view.horarios.FiltroGrupos',
{
    extend : 'Ext.panel.Panel',
    alias : 'widget.filtroGrupos',
    border : false,
    padding : 5,
    closable : false,
    comboTitulacion : null,
    comboCurso : null,
    comboSemestre : null,
    comboGrupo : null,
    comboTipoHorario : null,
    initComponent : function()
    {
        this.callParent(arguments);
        this.initUI();
        this.add(this.comboTitulacion);
        this.add(this.comboCurso);
        this.add(this.comboSemestre);
        this.add(this.comboGrupo);
        this.add(this.comboTipoHorario);
    },

    initUI : function()
    {
        this.buildComboTitulacion();
        this.buildComboCurso();
        this.buildComboSemestre();
        this.buildComboGrupo();
        this.buildComboTipoHorario();
    },

    buildComboTitulacion : function()
    {
        this.comboTitulacion = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Titulació',
            store : 'StoreEstudios',
            editable : false,
            displayField : 'nombre',
            valueField : 'id',
            width : 700,
            itemId : 'titulaciones',
        });
    },

    buildComboCurso : function()
    {
        this.comboCurso = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Curso',
            store : 'StoreCursos',
            editable : false,
            displayField : 'curso',
            valueField : 'curso',
            itemId : 'cursos',
            lastQuery : ''
        });
    },

    buildComboSemestre : function()
    {
        this.comboSemestre = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Semestre',
            store : 'StoreSemestres',
            editable : false,
            displayField : 'semestre',
            valueField : 'semestre',
            itemId : 'semestres',
            lastQuery : ''
        });
    },

    buildComboGrupo : function()
    {
        this.comboGrupo = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Grupo',
            store : 'StoreGrupos',
            editable : false,
            displayField : 'grupo',
            valueField : 'grupo',
            itemId : 'grupos',
            lastQuery : ''
        });
    },

    buildComboTipoHorario : function()
    {
        var horariosStore = Ext.create("Ext.data.Store",
        {
            fields : [ "id", "horario" ],
            data : [
            {
                "id" : "Ma",
                "horario" : "Matí"
            },
            {
                "id" : "Ta",
                "horario" : "Vesprada"
            },
            {
                "id" : "Mi",
                "horario" : "Mixte"
            } ]
        });

        this.comboTipoHorario = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Horario',
            store : horariosStore,
            queryModel : 'local',
            editable : false,
            displayField : 'horario',
            valueField : 'id',
            itemId : 'horarios',
            value : 'Mi'
        });
    },

    getActiveCalendarType : function()
    {
        return this.down('#horarios').getValue();
    }
});