package com.lucas.loja.controller;

import static com.lucas.loja.dto.fromdto.FromDTO.fromDTOProdutoEmEstoque;

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

import com.lucas.loja.domain.produto.ProdutoEmEstoque;
import com.lucas.loja.dto.ProdutoEmEstoqueDTO;
import com.lucas.loja.service.EstoqueService;

@RestController
@RequestMapping(value = "/loja/estoque")
public class EstoqueController {
	
	@Autowired
	private EstoqueService estoqueService;

	@GetMapping(value = "/consulta")
	public ResponseEntity<List<ProdutoEmEstoqueDTO>> listarTodoEstoque() {
		
		List<ProdutoEmEstoque> produtoEmEstoque = estoqueService.findAllProdutosEmEstoque();
		List<ProdutoEmEstoqueDTO> dto = produtoEmEstoque.stream().map(produto -> new ProdutoEmEstoqueDTO(produto))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping(value = "/cadastro")
	public ResponseEntity<List<ProdutoEmEstoque>> cadastrarProduto(@RequestBody List<ProdutoEmEstoqueDTO> produtoParaSerCadastradoDTO) {
		
		List<ProdutoEmEstoque> ProdutosParaSeremCadastrados = produtoParaSerCadastradoDTO.stream()
				.map(prod -> fromDTOProdutoEmEstoque(prod)).collect(Collectors.toList());
		
		salvarCadaProdutoNoEstoque(ProdutosParaSeremCadastrados);
		return ResponseEntity.ok().body(ProdutosParaSeremCadastrados);
	}
	
	@DeleteMapping(value = "/deletar/{id}")
	public ResponseEntity<String> deleteProduto(@PathVariable String id) {
		estoqueService.deleteProduto(id);
		return ResponseEntity.ok().body("O Produto do id: " + id + " foi excluído");
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<String> updateEstoque(@RequestBody ProdutoEmEstoqueDTO produtoEmEstoqueDTO,
			@PathVariable String id) {
		ProdutoEmEstoque produtoEmEstoqueAtualizado = fromDTOProdutoEmEstoque(produtoEmEstoqueDTO);
		produtoEmEstoqueAtualizado.setId(id);
		estoqueService.updateProdutoEmEstoque(produtoEmEstoqueAtualizado);
		return ResponseEntity.ok().body("O produto: " + produtoEmEstoqueAtualizado.getNome() + " foi atualizado com sucesso!");
	}
	
	private void salvarCadaProdutoNoEstoque(List<ProdutoEmEstoque> ProdutosParaSeremCadastrados) {
		for (ProdutoEmEstoque produtoParaSerCadastrado : ProdutosParaSeremCadastrados) {
			estoqueService.saveProdutoNoEstoque(produtoParaSerCadastrado);
		}
	}
}
