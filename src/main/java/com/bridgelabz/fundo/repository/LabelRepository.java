package com.bridgelabz.fundo.repository;

import java.util.List;

import com.bridgelabz.fundo.model.Label;
import com.bridgelabz.fundo.model.Note;

public interface LabelRepository {

	void saveNoteLabels(Note notes);

	void saveLabel(Label label);

	Label getLabelByLabelId(Integer labelId);

	void deleteLabel(Integer labelId);

	List<Label> getLabel(Integer id);

	void getId(Integer id);

}
