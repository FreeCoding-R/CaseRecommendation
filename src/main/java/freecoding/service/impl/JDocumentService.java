package freecoding.service.impl;

import freecoding.entity.JDocument;

import java.util.List;

/**
 * Created by loick on 15/07/2017.
 */
public interface JDocumentService {
    List<JDocument> findAll() throws Exception;

    JDocument findByCharacter(String character) throws Exception;

    List<JDocument> findByCharacterLike(String character) throws Exception;

    List<JDocument> findAllByPage(int page, int rows) throws Exception;

    List<JDocument> findByInfoLike(String info) throws Exception;

}
