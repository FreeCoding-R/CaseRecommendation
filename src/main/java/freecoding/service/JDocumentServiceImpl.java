package freecoding.service;

import freecoding.dao.JDocumentRepository;
import freecoding.entity.JDocument;
import freecoding.service.impl.JDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created by loick on 15/07/2017.
 */
public class JDocumentServiceImpl implements JDocumentService {
    @Autowired
    private JDocumentRepository repository;

    @Override
    public List<JDocument> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public JDocument findByCharacter(String character) throws Exception {
        return repository.findJDocumentByCharacter(character);
    }

    @Override
    public List<JDocument> findByCharacterLike(String character) throws Exception {
        return repository.findByCharacterLike(character);
    }

    @Override
    public List<JDocument> findAllByPage(int page, int rows) throws Exception {
        PageRequest pageRequest = new PageRequest(page-1, rows);
        return (List<JDocument>) repository.findAll(pageRequest);
    }

    @Override
    public List<JDocument> findByInfoLike(String info) throws Exception {
        return repository.findByInfoLike(info);
    }


}
