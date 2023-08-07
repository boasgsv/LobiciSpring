package br.ufscar.dc.dsw.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.ILocacaoService;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {
	
	@Autowired
	private ILocacaoService service;
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Locacao locacao) {
		String data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		locacao.setCliente(getCliente());

		locacao.setData(data);
		return "locacao/cadastro";
	}
	
	private Cliente getCliente() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return clienteService.buscarPorEmail(usuarioDetails.getUsuario().getEmail());
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
					
		model.addAttribute("locacoes",service.buscarTodosPorCliente(getCliente()));
		
		return "locacao/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Locacao locacao, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "locacao/cadastro";
		}
		
		service.salvar(locacao);
		attr.addFlashAttribute("sucess", "locacao.create.sucess");
		return "redirect:/locacoes/listar";
	}
	
	@ModelAttribute("clientes")
	public List<Cliente> listaClientes() {
		return clienteService.buscarTodos();
	}
}