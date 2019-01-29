package com.lucas.loja.controller;

import static com.lucas.loja.dto.fromdto.FromDTO.fromDTOEndereco;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.loja.domain.Endereco;
import com.lucas.loja.dto.EnderecoDTO;
import com.lucas.loja.service.EnderecoService;

@RestController
@RequestMapping(value = "/loja/endereco")
public class EnderecoController {

	@Autowired
	public EnderecoService enderecoService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<EnderecoDTO>> listarEnderecos(){
		List<Endereco> enderecos = enderecoService.findAllEnderecos();
		List<EnderecoDTO> dto = enderecos.stream().map(endereco -> new EnderecoDTO(endereco)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
		Endereco endereco = fromDTOEndereco(enderecoDTO);
		enderecoService.saveEndereco(endereco);
		return ResponseEntity.ok().body(endereco);

	}
}
