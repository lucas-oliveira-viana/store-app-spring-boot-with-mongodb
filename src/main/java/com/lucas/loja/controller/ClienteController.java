package com.lucas.loja.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.loja.controller.utils.Decoder;
import com.lucas.loja.controller.utils.FromDTO;
import com.lucas.loja.controller.utils.ToDTO;
import com.lucas.loja.dto.ClienteDTO;
import com.lucas.loja.entities.Cliente;
import com.lucas.loja.service.ClienteService;

@RestController
@RequestMapping(value = "/loja/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping(value = "/consulta")
	public ResponseEntity<List<ClienteDTO>> listarClientes(){
		List<Cliente> clientes = clienteService.findAllClientes();
		List<ClienteDTO> dto = ToDTO.passarClienteParaDTO(clientes);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/filtrarporcpf")
	public ResponseEntity<ClienteDTO> filtrarClientePorCpf(@RequestParam (value = "cpf", defaultValue = "") String cpf){
		cpf = Decoder.inserirPontuacoesCPF(cpf);
		Cliente clientesQueContemEsseCpf = clienteService.findClienteByCpf(cpf);
		ClienteDTO dto = new ClienteDTO(clientesQueContemEsseCpf);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody ClienteDTO clienteDTO) {
		Cliente cliente = FromDTO.fromDTOCliente(clienteDTO);
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
		Cliente clienteAtualizado = FromDTO.fromDTOCliente(clienteDTO);
		clienteAtualizado.setId(id);
		clienteService.updateCliente(clienteAtualizado);
		return ResponseEntity.ok().body("O cadastro de: " + clienteAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}