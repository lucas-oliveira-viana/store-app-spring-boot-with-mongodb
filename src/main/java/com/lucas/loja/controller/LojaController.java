package com.lucas.loja.controller;

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

import com.lucas.loja.domain.Compra;
import com.lucas.loja.domain.Funcionario;
import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.dto.CompraDTO;
import com.lucas.loja.dto.FuncionarioDTO;
import com.lucas.loja.dto.ProdutoEmEstoqueDTO;
import com.lucas.loja.dto.fromdto.FromDTO;
import com.lucas.loja.service.CompraService;

@RestController
@RequestMapping(value = "/loja")
public class LojaController {

	@Autowired
	private CompraService compraService;
	
	private FromDTO fromDTO = new FromDTO();
	
	@GetMapping
	public ResponseEntity<List<CompraDTO>> listarTodasCompras(){
		List<Compra> compras = compraService.findAllCompras();
		List<CompraDTO> dto = compras.stream().map(compra -> new CompraDTO(compra)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/consultarestoque")
	public ResponseEntity<List<ProdutoEmEstoqueDTO>> listarTodoEstoque(){
		List<ProdutoEmEstoque> produtoEmEstoque = compraService.findAllProdutosEmEstoque();
		List<ProdutoEmEstoqueDTO> dto = produtoEmEstoque.stream().map(produto -> new ProdutoEmEstoqueDTO(produto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/compra/{id}")
	public ResponseEntity<CompraDTO> listaCompraById(@PathVariable String id){
		Compra compra = compraService.findComprasById(id);
		return ResponseEntity.ok().body(new CompraDTO(compra));
	}
	
	@PostMapping(value = "/cadastrarproduto")
	public ResponseEntity<List<ProdutoEmEstoque>> cadastrarProduto(@RequestBody List<ProdutoEmEstoqueDTO> produtoParaSerCadastradoDTO){
		List<ProdutoEmEstoque> ProdutosParaSeremCadastrados = produtoParaSerCadastradoDTO.stream().map(prod -> fromDTO.fromDTOProdutoEmEstoque(prod)).collect(Collectors.toList());
		for (ProdutoEmEstoque produtoParaSerCadastrado : ProdutosParaSeremCadastrados) {
			compraService.saveProdutoNoEstoque(produtoParaSerCadastrado);
		}
		return ResponseEntity.ok().body(ProdutosParaSeremCadastrados);
	}
	
	@PostMapping(value = "/cadastrarfuncionario")
	public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO){
		Funcionario funcionario = fromDTO.fromDTOFuncionario(funcionarioDTO);
		compraService.saveFuncionario(funcionario);
		return ResponseEntity.ok().body(funcionario);
	}
	
	@PostMapping(value = "/realizarcompra")
	public ResponseEntity<Compra> realizarCompra(@RequestBody CompraDTO compraDTO){
		Compra compra = fromDTO.fromDTOCompra(compraDTO);
		compraService.insertCompra(compra);
		return ResponseEntity.ok().body(compra);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteCompra(@PathVariable String id){
		compraService.deleteCompra(id);
		return ResponseEntity.ok().body("A Compra do id: " + id + " foi excluída");
	}
	
	@PutMapping(value = "/atualizarcompra/{id}")
	public ResponseEntity<String> updateCompra(@RequestBody CompraDTO compraAtualizadaDTO, @PathVariable String id){
		Compra compraAtualizada = fromDTO.fromDTOCompra(compraAtualizadaDTO);
		compraAtualizada.setId(id);
		compraService.updateCompra(compraAtualizada);
		return ResponseEntity.ok().body("A compra de: " + compraAtualizada.getCliente().getNome() + " foi atualizada com sucesso!");
	}
	
	@PutMapping(value = "/atualizarestoque/{id}")
	public ResponseEntity<String> updateEstoque(@RequestBody ProdutoEmEstoqueDTO produtoEmEstoqueDTO, @PathVariable String id){
		ProdutoEmEstoque produtoEmEstoqueAtualizado = fromDTO.fromDTOProdutoEmEstoque(produtoEmEstoqueDTO);
		produtoEmEstoqueAtualizado.setId(id);
		compraService.updateProdutoEmEstoque(produtoEmEstoqueAtualizado);
		return ResponseEntity.ok().body("O produto: " + produtoEmEstoqueAtualizado.getNome() + " foi atualizado com sucesso!");
	}
}
