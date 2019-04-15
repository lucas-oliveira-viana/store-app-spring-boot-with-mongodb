package com.lucas.loja.controller;

import static com.lucas.loja.controller.utils.ToDTO.passarEnderecoParaDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.loja.controller.utils.FromDTO;
import com.lucas.loja.dto.EnderecoDTO;
import com.lucas.loja.entities.Endereco;
import com.lucas.loja.service.EnderecoService;

@RestController
@RequestMapping(value = "/loja/endereco")
public class EnderecoController {

	@Autowired
	public EnderecoService enderecoService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<EnderecoDTO>> listarEnderecos(){
		List<Endereco> enderecos = enderecoService.findAllEnderecos();
		List<EnderecoDTO> dto = passarEnderecoParaDTO(enderecos);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO) {
		Endereco endereco = FromDTO.fromDTOEndereco(enderecoDTO);
		enderecoService.saveEndereco(endereco);
		return ResponseEntity.ok().body(endereco);

	}
}
