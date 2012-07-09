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
    initComponent : function()
    {
        this.callParent(arguments);
        this.initUI();
        this.add(this.comboTitulacion);
        this.add(this.comboCurso);
        this.add(this.comboSemestre);
        this.add(this.comboGrupo);
    },

    initUI : function()
    {
        this.buildComboTitulacion();
        this.buildComboCurso();
        this.buildComboSemestre();
        this.buildComboGrupo();
    },

    buildComboTitulacion : function()
    {
        this.comboTitulacion = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Titulació',
            store : 'StoreEstudios',
            queryModel : 'local',
            displayField : 'nombre',
            valueField : 'id',
            width : 700,
            itemId : 'titulaciones',
            editable : false,
        });
    },

    buildComboCurso : function()
    {
        this.comboCurso = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Curso',
            store : 'StoreCursos',
            queryModel : 'local',
            displayField : 'curso',
            valueField : 'curso',
            itemId : 'cursos',
            editable : false,
            lastQuery : ''
        });
    },

    buildComboSemestre : function()
    {
        this.comboSemestre = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Semestre',
            store : 'StoreSemestres',
            queryModel : 'local',
            displayField : 'semestre',
            valueField : 'semestre',
            itemId : 'semestres',
            editable : false,
            lastQuery : ''
        });
    },

    buildComboGrupo : function()
    {
        this.comboGrupo = Ext.create("Ext.form.ComboBox",
        {
            fieldLabel : 'Grupo',
            store : 'StoreGrupos',
            queryModel : 'local',
            displayField : 'grupo',
            valueField : 'grupo',
            itemId : 'grupos',
            editable : false,
            lastQuery : ''
        });
    }
});