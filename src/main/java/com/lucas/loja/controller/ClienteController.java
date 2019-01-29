package com.lucas.loja.controller;

import static com.lucas.loja.dto.fromdto.FromDTO.fromDTOCliente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.loja.domain.Cliente;
import com.lucas.loja.dto.ClienteDTO;
import com.lucas.loja.service.ClienteService;

@RestController
@RequestMapping(value = "/loja/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<ClienteDTO>> listarClientes(){
		List<Cliente> clientes = clienteService.findAllClientes();
		List<ClienteDTO> dto = clientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = fromDTOCliente(clienteDTO);
		clienteService.saveCliente(cliente);
		return ResponseEntity.ok().body(cliente);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deleteCliente(@PathVariable String id) {
		clienteService.deleteCliente(id);
		return ResponseEntity.ok().body("O Cliente do id: " + id + " foi excluído");
	}
	
	@PutMapping(value = "atualizar/{id}")
	public ResponseEntity<String> updateFuncionario(@RequestBody ClienteDTO clienteDTO, @PathVariable String id){
		Cliente clienteAtualizado = fromDTOCliente(clienteDTO);
		clienteAtualizado.setId(id);
		clienteService.updateCliente(clienteAtualizado);
		return ResponseEntity.ok().body("O cadastro de: " + clienteAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}