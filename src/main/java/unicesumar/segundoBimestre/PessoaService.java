package unicesumar.segundoBimestre;

import mySpringBootApp.livro.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> findAll() {

        return this.repository.findAll();
    }

    public Pessoa findById(UUID id) {

        return this.repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public void save(Pessoa pessoa) {

        this.repository.save(pessoa);
    }

    @Transactional
    public void delete(UUID id) {

        this.repository.delete(this.findById(id));
    }
}
