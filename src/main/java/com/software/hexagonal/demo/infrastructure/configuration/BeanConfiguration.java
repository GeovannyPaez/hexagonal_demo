package com.software.hexagonal.demo.infrastructure.configuration;

import com.software.hexagonal.demo.domain.api.ICategoriaServicePort;
import com.software.hexagonal.demo.domain.api.IClienteServicePort;
import com.software.hexagonal.demo.domain.api.IDetallePedidoServicePort;
import com.software.hexagonal.demo.domain.api.IPedidoServicePort;
import com.software.hexagonal.demo.domain.api.IProductoServicePort;
import com.software.hexagonal.demo.domain.spi.ICategoriaPersistencePort;
import com.software.hexagonal.demo.domain.spi.IClientePersistencePort;
import com.software.hexagonal.demo.domain.spi.IDetallePedidoPersistencePort;
import com.software.hexagonal.demo.domain.spi.IPedidoPersistencePort;
import com.software.hexagonal.demo.domain.spi.IProductoPersistencePort;
import com.software.hexagonal.demo.domain.usecase.CategoriaUseCase;
import com.software.hexagonal.demo.domain.usecase.ClienteUseCase;
import com.software.hexagonal.demo.domain.usecase.DetallePedidoUseCase;
import com.software.hexagonal.demo.domain.usecase.PedidoUseCase;
import com.software.hexagonal.demo.domain.usecase.ProductoUseCase;
import com.software.hexagonal.demo.infrastructure.output.jpa.adapter.CategoriaJpaAdapter;
import com.software.hexagonal.demo.infrastructure.output.jpa.adapter.ClienteJpaAdapter;
import com.software.hexagonal.demo.infrastructure.output.jpa.adapter.DetallePedidoJpaAdapter;
import com.software.hexagonal.demo.infrastructure.output.jpa.adapter.PedidoJpaAdapter;
import com.software.hexagonal.demo.infrastructure.output.jpa.adapter.ProductoJpaAdapter;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.ICategoriaRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IClienteRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IDetallePedidoRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IPedidoRepository;
import com.software.hexagonal.demo.infrastructure.output.jpa.repository.IProductoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ICategoriaPersistencePort categoriaPersistencePort(ICategoriaRepository categoriaRepository) {
        return new CategoriaJpaAdapter(categoriaRepository);
    }

    @Bean
    public IProductoPersistencePort productoPersistencePort(IProductoRepository productoRepository,
                                                            ICategoriaRepository categoriaRepository) {
        return new ProductoJpaAdapter(productoRepository, categoriaRepository);
    }

    @Bean
    public IClientePersistencePort clientePersistencePort(IClienteRepository clienteRepository) {
        return new ClienteJpaAdapter(clienteRepository);
    }

    @Bean
    public IPedidoPersistencePort pedidoPersistencePort(IPedidoRepository pedidoRepository,
                                                        IClienteRepository clienteRepository) {
        return new PedidoJpaAdapter(pedidoRepository, clienteRepository);
    }

    @Bean
    public IDetallePedidoPersistencePort detallePedidoPersistencePort(IDetallePedidoRepository detallePedidoRepository,
                                                                      IPedidoRepository pedidoRepository,
                                                                      IProductoRepository productoRepository) {
        return new DetallePedidoJpaAdapter(detallePedidoRepository, pedidoRepository, productoRepository);
    }

    @Bean
    public ICategoriaServicePort categoriaServicePort(ICategoriaPersistencePort categoriaPersistencePort,
                                                      IProductoPersistencePort productoPersistencePort) {
        return new CategoriaUseCase(categoriaPersistencePort, productoPersistencePort);
    }

    @Bean
    public IProductoServicePort productoServicePort(IProductoPersistencePort productoPersistencePort,
                                                    ICategoriaPersistencePort categoriaPersistencePort,
                                                    IDetallePedidoPersistencePort detallePedidoPersistencePort) {
        return new ProductoUseCase(productoPersistencePort, categoriaPersistencePort, detallePedidoPersistencePort);
    }

    @Bean
    public IClienteServicePort clienteServicePort(IClientePersistencePort clientePersistencePort,
                                                  IPedidoPersistencePort pedidoPersistencePort) {
        return new ClienteUseCase(clientePersistencePort, pedidoPersistencePort);
    }

    @Bean
    public IPedidoServicePort pedidoServicePort(IPedidoPersistencePort pedidoPersistencePort,
                                                IClientePersistencePort clientePersistencePort) {
        return new PedidoUseCase(pedidoPersistencePort, clientePersistencePort);
    }

    @Bean
    public IDetallePedidoServicePort detallePedidoServicePort(IDetallePedidoPersistencePort detallePedidoPersistencePort,
                                                              IPedidoPersistencePort pedidoPersistencePort,
                                                              IProductoPersistencePort productoPersistencePort) {
        return new DetallePedidoUseCase(detallePedidoPersistencePort, pedidoPersistencePort, productoPersistencePort);
    }
}
