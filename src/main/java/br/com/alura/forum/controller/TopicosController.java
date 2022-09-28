package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController //não precisa colocar mais o responsebody
@RequestMapping("/topicos") //a mesma url usado para dois metodos um GET e outro POST
public class TopicosController {
	
	@Autowired //injeção de dependencia
	private TopicoRepository topicoRepository;

	@Autowired //injeção de dependencia
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
	}
	@PostMapping                      //@Valid quando for puxar as informações rode as validações TopicoForm
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
			
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); //criando uma uri dinamica
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
		
	}
	
	@GetMapping("/{id}")//id dinamico 
	public DetalhesDoTopicoDto detalhar(@PathVariable Long id) {
		Topico topico = topicoRepository.getOne(id); //carregar no banco de dados.
		return new DetalhesDoTopicoDto(topico);	
	}
	
	@PutMapping("/{id}")
	@Transactional //avisa pro spring comitar transação no final do metodo caso não ocorra uma exception, 
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form ){
		Topico topico = form.atualizar(id, topicoRepository);
		
		return ResponseEntity.ok(new TopicoDto(topico));
	}
	
}
